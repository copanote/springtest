package io;



/*
  Path클래스의 두 가지 용도
    1. 파일시스템에 접근하지 않고 경로를 조작하는 작업에 주로 사용
    2. 경로(path)를 참조하는 파일에 대한 작업에 쓰인다.
 */

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


@Slf4j
public class PathTest {

    @Test
    public void test_path() {
        Path path = Paths.get("/etc/index.html");
        Path path1 = Paths.get("/", "etc/index.html");
        Path path2 = Paths.get("/", "etc", "index.html");

        log.info("{}", path);
        log.info("{}", path1);
        log.info("{}", path2);

    }


    @Test
    void test_supportedFileAttributeView() throws IOException {
        FileSystem fs = FileSystems.getDefault();
        for (String supportedFileAttributeView : fs.supportedFileAttributeViews()) {
            log.info(supportedFileAttributeView);
        }

        Path readme = Paths.get("/Users/copa/project/springtest/README.md");
        BasicFileAttributes basicFileAttributes = Files.readAttributes(readme, BasicFileAttributes.class);
        log.info("{}", basicFileAttributes.creationTime());
        log.info("{}", basicFileAttributes.size());
        log.info("{}", basicFileAttributes.isDirectory());
        log.info("{}", basicFileAttributes.isRegularFile());

        log.info("{}", Files.exists(readme));
        log.info("{}", Files.notExists(readme));


    }
}
