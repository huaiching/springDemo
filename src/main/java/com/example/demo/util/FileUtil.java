package com.example.demo.util;

import com.deepoove.poi.XWPFTemplate;
import com.example.demo.dto.ZipFlieDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
     * 產生 Word (poi-tl)
     * @param modelFile 樣板路徑
     * @param context 內容
     * @return Word 資料流
     */
    public static byte[] generateWord(String modelFile, Map<String, Object> context) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            XWPFTemplate template = XWPFTemplate.compile(new ClassPathResource(modelFile).getInputStream());
            template.render(context);
            template.writeAndClose(outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
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
     * @param pdfFlieLisst 要合併的 PDF 清單
     * @return PDF 資料流
     */
    public static byte[] mergePDF(List<byte[]> pdfFlieLisst) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfCopy pdfCopy = new PdfCopy(document, outputStream);
            document.open();
            // PDF合併
            for (byte[] pdfFlie : pdfFlieLisst) {
                PdfReader pdfReader = new PdfReader(pdfFlie);
                int pdfPages = pdfReader.getNumberOfPages();
                for (int i = 1; i <= pdfPages; i++) {
                    PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, i);
                    pdfCopy.addPage(importedPage);
                }
                pdfReader.close();
            }
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
     * 檔案格式 使用的 ResponseEntity
     * @param fileName  檔案名稱
     * @param fileBytes 檔案資料流
     * @return 檔案格式 ResponseEntity
     */
    public static ResponseEntity<Resource> responseEntity(String fileName, byte[] fileBytes) throws UnsupportedEncodingException {
        // 文件打包
        Resource resource = new ByteArrayResource(fileBytes);
        // 文件下載
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));

        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
