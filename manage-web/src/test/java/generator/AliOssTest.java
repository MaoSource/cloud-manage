package generator;

import com.source.boot;
import com.source.system.bean.OssBean;
import com.source.system.service.AliOssService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/12/10/11:18
 * @Description:
 */
@SpringBootTest(classes = boot.class)
@Slf4j
public class AliOssTest {

    @Autowired
    private OssBean bean;

    @Autowired
    private AliOssService aliOssService;

    @Test
    public void testOss(){
        log.info("bean"+ bean.getBucketName());
    }

    @Test
    public void list(){
        aliOssService.listFile();
    }

    @Test
    public void delete(){
        aliOssService.deleteFile("2020/12/10/zip-29f12662633742eb936f4e98d797e93e.zip");
    }
}
