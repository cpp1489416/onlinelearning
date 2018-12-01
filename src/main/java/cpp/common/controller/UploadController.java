package cpp.common.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import cpp.common.OutInfo;
import cpp.common.dto.FileOutInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("upload")
public class UploadController {
    static final String QiniuAccessKey = "ihC_g0dhSqU18nk7VlapE4b7itZUoyWmRkjA--_h";
    static final String QiniuSecretKey = "NVpAS_Q4ze-gaQrhKQ6_SmOQa3cKw5GObycuB3vS";
    static final String QiniuBucket = "onlinelearning2";
    static final String QiniuCdns = "piq1xmrhl.bkt.clouddn.com";


    // 测试用的接口
    @RequestMapping("upload2.action")
    @ResponseBody
    public FileOutInfo imgUpload2(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request) {
        String originFileName = upfile.getOriginalFilename();
        String fileName = RandomStringUtils.randomAlphanumeric(20)
                + originFileName.substring(originFileName.lastIndexOf("."));
        File newFile = new File(request.getServletContext().getRealPath("/upload"), fileName);
        newFile.getParentFile().mkdirs();
        try {
            upfile.transferTo(newFile);
            FileOutInfo out = new FileOutInfo();
            out.setUrl(request.getServletContext().getContextPath() + "/upload/" + newFile.getName());

            return out;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }


    // 测试用的就扣
    @RequestMapping("test.action")
    @ResponseBody
    public OutInfo test() {
        return OutInfo.success();
    }

    // 通过七牛云上传文件
    @RequestMapping("upload.action")
    @ResponseBody
    public FileOutInfo upload(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request) {
        String originFileName = upfile.getOriginalFilename();
        String fileName = RandomStringUtils.randomAlphanumeric(20)
                + originFileName.substring(originFileName.lastIndexOf("."));
        Configuration cfg = new Configuration(Zone.autoZone());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = QiniuAccessKey;
        String secretKey = QiniuSecretKey;
        String bucket = QiniuBucket;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        try {
            byte[] uploadBytes = upfile.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    ex2.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            FileOutInfo out = new FileOutInfo();
            out.setUrl("http://" + QiniuCdns + "/" + fileName);
            return out;
        } catch (Exception e) {
            return null;
        }

    }

    /*
    public static void main(String[] args) throws Exception {
        System.out.println("hello");
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = QiniuAccessKey;
        String secretKey = QiniuSecretKey;
        String bucket = QiniuBucket;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, "a.png", upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }
    }*/
}
