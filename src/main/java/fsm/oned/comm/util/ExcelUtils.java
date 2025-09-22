package fsm.oned.comm.util;

import fsm.oned.comm.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class ExcelUtils {
    /**
     * 엑셀 템플릿 파일을 Return
     *
     * @return
     */
    public Workbook getWorkbookTemplate(String fileName) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Workbook workbook = null;
        final String ROOT_PATH = req.getSession().getServletContext().getRealPath("/");
        final String ATTACH_PATH = "/templateTemp/";

        try (FileInputStream template = new FileInputStream(new File(ROOT_PATH + ATTACH_PATH + fileName))) {
            workbook = WorkbookFactory.create(template);
        }catch (NullPointerException e) {
            throw new UserException(e,"관리자에게 문의하세요");
        }catch (Exception e) {
            throw new UserException(e,"관리자에게 문의하세요");
        }
        return workbook;
    }

    /**
     * 가운데 정렬 셀스타일 Return
     * @return
     */
    public CellStyle getCenterCellStyle(Workbook workbook , Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        this.setCellBorder(cellStyle);

        //cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 쉼표(,)구분 숫자 스타일
     * @return
     */
    public CellStyle getNumberWithCommaCellStyle(Workbook workbook , Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        this.setCellBorder(cellStyle);

        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

        //cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle.setFont(font);
        return cellStyle;
    }


    /**
     * 소수점 있는 숫자 스타일
     * @return
     */
    public CellStyle getDemicalWithCommaCellStyle(Workbook workbook , Font font ,int decimalCount) {
        CellStyle cellStyle = workbook.createCellStyle();
        this.setCellBorder(cellStyle);

        Short dataFormat = 0;
        if (decimalCount == 1) {
            dataFormat = workbook.createDataFormat().getFormat("#,##0.0");
        } else if (decimalCount == 2) {
            dataFormat = workbook.createDataFormat().getFormat("#,##0.00");
        } else if (decimalCount == 3) {
            dataFormat = workbook.createDataFormat().getFormat("#,##0.000");
        } else {
            dataFormat = workbook.createDataFormat().getFormat("#,##0");
        }

        cellStyle.setDataFormat(dataFormat);
        //cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle.setFont(font);
        return cellStyle;
    }


    /**
     * default Font
     * @return
     */
    public Font getDefaultFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("맑은 고딕");
        return font;
    }

    /**
     * 셀 border 설정
     * @return
     */
    public void setCellBorder(CellStyle cellStyle) {
        //cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        //cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        //cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    }

}