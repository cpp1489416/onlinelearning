package cpp.common.controller;

import cpp.common.OutInfo;
import cpp.common.dto.FileOutInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// 百度的编辑器
@Controller
public class UEditorController {
    @Autowired
    UploadController uploadController;

    // 百度上传图片转到七牛云
    @RequestMapping(value = "/ueditorImageUpload.action")
    @ResponseBody
    public String imgUpload(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request) { // 参数名称一定是upfile
        FileOutInfo out = uploadController.upload(upfile, request);
        String url = out.getUrl();
        try {
            // url的值为图片的实际访问地址 这里我用了Nginx代理，访问的路径是http://localhost/xxx.png
            String config =
                    "{\"state\": \"SUCCESS\"," +
                            "\"url\": \"" +url +  "\"," +
                            "\"title\": \"" + "abc" + "\"," +
                            "\"original\": \"" + "abc" + "\"}";
            return config;

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    // 百度的默认配置信息
    @RequestMapping("/commons/ueditor-1.4.3.3/jsp/controller.action")
    public void controllerJsp(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        String ans =
                "{\r\n" +
                        "    \"imageActionName\": \"uploadimage\", \r\n" +
                        "    \"imageFieldName\": \"upfile\", \r\n" +
                        "    \"imageMaxSize\": 2048000, \r\n" +
                        "    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \r\n" +
                        "    \"imageCompressEnable\": true, \r\n" +
                        "    \"imageCompressBorder\": 1600,\r\n" +
                        "    \"imageInsertAlign\": \"none\",\r\n" +
                        "    \"imageUrlPrefix\": \"\",\r\n" +
                        "    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "                               \r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"scrawlActionName\": \"uploadscrawl\",\r\n" +
                        "    \"scrawlFieldName\": \"upfile\",\r\n" +
                        "    \"scrawlPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "    \"scrawlMaxSize\": 2048000,\r\n" +
                        "    \"scrawlUrlPrefix\": \"\",\r\n" +
                        "    \"scrawlInsertAlign\": \"none\",\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"snapscreenActionName\": \"uploadimage\",\r\n" +
                        "    \"snapscreenPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "    \"snapscreenUrlPrefix\": \"\",\r\n" +
                        "    \"snapscreenInsertAlign\": \"none\",\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"catcherLocalDomain\": [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],\r\n" +
                        "    \"catcherActionName\": \"catchimage\",\r\n" +
                        "    \"catcherFieldName\": \"source\",\r\n" +
                        "    \"catcherPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "    \"catcherUrlPrefix\": \"\",\r\n" +
                        "    \"catcherMaxSize\": 20480000000,\r\n" +
                        "    \"catcherAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"videoActionName\": \"uploadvideo\",\r\n" +
                        "    \"videoFieldName\": \"upfile\",\r\n" +
                        "    \"videoPathFormat\": \"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "    \"videoUrlPrefix\": \"\",\r\n" +
                        "    \"videoMaxSize\": 102400000000,\r\n" +
                        "    \"videoAllowFiles\": [\r\n" +
                        "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" +
                        "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"],\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"fileActionName\": \"uploadfile\",\r\n" +
                        "    \"fileFieldName\": \"upfile\",\r\n" +
                        "    \"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\r\n" +
                        "    \"fileUrlPrefix\": \"\",\r\n" +
                        "    \"fileMaxSize\": 512000000000,\r\n" +
                        "    \"fileAllowFiles\": [\r\n" +
                        "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n" +
                        "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" +
                        "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n" +
                        "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n" +
                        "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n" +
                        "    ],\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"imageManagerActionName\": \"listimage\",\r\n" +
                        "    \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\",\r\n" +
                        "    \"imageManagerListSize\": 20,\r\n" +
                        "    \"imageManagerUrlPrefix\": \"\",\r\n" +
                        "    \"imageManagerInsertAlign\": \"none\",\r\n" +
                        "    \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],\r\n" +
                        "\r\n" +
                        "   \r\n" +
                        "    \"fileManagerActionName\": \"listfile\",\r\n" +
                        "    \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\",\r\n" +
                        "    \"fileManagerUrlPrefix\": \"\",\r\n" +
                        "    \"fileManagerListSize\": 20,\r\n" +
                        "    \"fileManagerAllowFiles\": [\r\n" +
                        "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n" +
                        "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" +
                        "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n" +
                        "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n" +
                        "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n" +
                        "    ]\r\n" +
                        "\r\n" +
                        "}";
        try {
            PrintWriter writer = response.getWriter();
            writer.write(ans);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
