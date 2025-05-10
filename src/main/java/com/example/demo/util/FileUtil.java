package com.example.demo.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.example.demo.dto.ZipFlieDto;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    /**
     * 產生 Excel (Jxls)
     * @param modelFile 樣板路徑
     * @param context 內容
     * @return Excel 資料流
     */
    public static byte[] generateExcel(String modelFile, Context context) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            InputStream inputStream = new ClassPathResource(modelFile).getInputStream();
            JxlsHelper.getInstance().setEvaluateFormulas(true).processTemplate(inputStream,outputStream,context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 產生 Word 檔案
     *
     * @param modelFile 樣板路徑（例如："templates/document_template.docx"，位於 classpath）
     * @param context   資料內容（Map 對應樣板中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWord(String modelFile, Map<String, Object> context) {
        try (
                InputStream inputStream = new ClassPathResource(modelFile).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            // 載入樣板並填入資料
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(context);

            // 將結果寫入 outputStream 並關閉資源
            template.writeAndClose(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Word 產生失敗，樣板路徑：" + modelFile, e);
        }
    }

    /**
     * 產生 Word 檔案 (合併列印)
     *
     * @param modelFile 樣板路徑（例如："templates/document_template.docx"，位於 classpath）
     * @param contextList   資料內容 清單（Map 對應樣板中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWord(String modelFile, List<Map<String, Object>> contextList) {
        if (contextList == null || contextList.isEmpty()) {
            throw new IllegalArgumentException("資料內容 不可空白!!");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 使用第一個文件作為基礎
            var firstWord = generateWord(modelFile, contextList.get(0));
            NiceXWPFDocument mainWord = new NiceXWPFDocument(new ByteArrayInputStream(firstWord));

            try {
                // 合併後續文件
                for (int i = 1; i < contextList.size(); i++) {
                    // 在合併前先加入分頁符
                    addPageBreak(mainWord);

                    var tmpWord = generateWord(modelFile, contextList.get(i));
                    NiceXWPFDocument subWord = new NiceXWPFDocument(new ByteArrayInputStream(tmpWord));
                    mainWord = mainWord.merge(subWord);
                    subWord.close();
                }

                mainWord.write(outputStream);
                return outputStream.toByteArray();

            } catch (Exception e) {
                throw new RuntimeException("Word 合併失敗：", e);
            } finally {
                mainWord.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Word 產生失敗，樣板路徑：" + modelFile, e);
        }
    }

    /**
     * 在文件末尾加入分頁符
     */
    private static void addPageBreak(NiceXWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);  // 加入分頁符
    }


    /**
     * word 轉 PDF (fr.opensagres.poi.xwpf.converter.pdf)
     * @param wordFile word 檔
     * @return PDF 資料流
     */
    public static byte[] wordToPDF(byte[] wordFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(wordFile));
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, outputStream, options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * PDF 檔案合併 (iText)
     * @param pdfFlieList 要合併的 PDF 清單
     * @return PDF 資料流
     */
    public static byte[] mergePDF(List<byte[]> pdfFlieList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // 創建 PDF 合併相關變數
            com.lowagie.text.Document document = new Document();
            com.lowagie.text.pdf.PdfCopy pdfCopy = new PdfCopy(document, outputStream);
            document.open();
            // PDF 合併
            for (byte[] pdfFlie : pdfFlieList) {
                // 讀取文件
                com.lowagie.text.pdf.PdfReader pdfReader = new PdfReader(pdfFlie);
                // 取得頁數
                int pdfPages = pdfReader.getNumberOfPages();
                // 添加至輸出文檔
                for (int pdfPage = 1; pdfPage <= pdfPages; pdfPage++) {
                    PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, pdfPage);
                    pdfCopy.addPage(importedPage);
                }
                // 關閉 PdfReader
                pdfReader.close();
            }
            // 關閉 Document
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 打包為 zip 檔
     * @param zipFileList 要打包的文件清單
     * @return zip 資料流
     */
    public static byte[] createZipFile(List<ZipFlieDto> zipFileList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            // zip 資料設定
            for (ZipFlieDto zipFlie : zipFileList) {
                zipOutputStream.putNextEntry(new ZipEntry(zipFlie.getFlieName()));
                zipOutputStream.write(zipFlie.getFileBytes());
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 產生 純文字檔 (自動換行) (BIG5)
     * @param contentList 每行的文字內容
     * @return 純文字檔 資料流
     */
    public static byte[] generateTxtBIG5(List<String> contentList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuilder stringBuilder = new StringBuilder();
        // 寫入內文 (自動換行)
        for (String content : contentList) {
            stringBuilder.append(content);
            stringBuilder.append("\r\n");
        }
        try {
            outputStream.write(stringBuilder.toString().getBytes("BIG5"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 產生 純文字檔 (自動換行) (UTF-8)
     * @param contentList 每行的文字內容
     * @return 純文字檔 資料流
     */
    public static byte[] generateTxtUTF8(List<String> contentList) {
        byte[] big5Bytes = generateTxtBIG5(contentList);

        // 將 Big5 byte[] 解碼為 String
        String decodedString = new String(big5Bytes, Charset.forName("Big5"));

        // 將 String 重新編碼為 UTF-8 byte[]
        return decodedString.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 文字格式化: 依照 最大字元數，自動補齊 或 擷取
     * @param text  文字內容
     * @param maxLength 最大字元數
     * @return  格式化後的文字
     */
    public static String formatTxt(String text, int maxLength) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        // 計算 文字長度: 利用 BIG5 中文 佔 2 byte 的特性
        Charset big5 = Charset.forName("BIG5");
        byte[] bytes = text.getBytes(Charset.forName("BIG5"));
        // 進行 文字的 擷取 與 補齊空格 的作業
        if (bytes.length > maxLength) {
            // 截取超過字節的部分
            return new String(bytes, 0, maxLength, big5);
        } else {
            // 補齊空格
            StringBuilder result = new StringBuilder(text);
            while (result.toString().getBytes(big5).length < maxLength) {
                result.append(" ");
            }
            return result.toString();
        }
    }


    /**
     * 產生 檔案下載的 ResponseEntity
     * @param fileName 檔案名稱
     * @param fileByte 檔案資料流
     * @return
     * @throws UnsupportedEncodingException
     */
    public static ResponseEntity<Resource> responseEntity(String fileName, byte[] fileByte) throws UnsupportedEncodingException {
        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, "UTF-8"));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
