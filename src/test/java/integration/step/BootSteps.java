package integration.step;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.github.tamurashingo.jalo.JaloRunner;
import com.github.tamurashingo.jalo.Main;
import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;

public class BootSteps {
    
    BootConfigBean bootConfig;
    AppConfigBean appConfig;
    Path tempPath;
    
    @BeforeStories
    public void before() {
        
    }
    
    @Given("初期化")
    public void initialize() {
    }
    
    @When("テンポラリディレクトリの作成")
    public void createTempDir() throws Exception {
        if (this.tempPath != null) {
            deletePath(this.tempPath);
            this.tempPath = null;
        }
        this.tempPath = Files.createTempDirectory(null);
    }
    
    @When("アプリケーションディレクトリの削除")
    public void deleteAppDir() throws Exception {
        Path appPath = this.tempPath.resolve(this.bootConfig.getApplicationDir());
        deletePath(appPath);
    }

    
    @When("Bootファイル読み込み $boot")
    public void loadBoot(String boot) throws Exception {
        URI bootURI = BootSteps.class.getClassLoader().getResource(boot).toURI();
        Path bootPath = Paths.get(bootURI);
        
        // テンポラリディレクトリにbootファイルをコピー
        Path tmpBootPath = Files.copy(bootPath, this.tempPath.resolve(bootPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        
        // テンポラリディレクトリのbootファイル名を得る
        String tmpBootFile = tmpBootPath.toString();
        
        // テンポラリディレクトリのbootファイルを読み込む
        this.bootConfig = new BootConfigBean();
        this.bootConfig.read(tmpBootFile);
    }
    
    @When("アプリケーション読み込み")
    public void loadApp() throws Exception {
        this.appConfig = AppConfigBean.createConfig(this.bootConfig);
        this.appConfig = Main.update(this.bootConfig, this.appConfig);
    }
    
    
    @Then("更新用URLが $url であること")
    public void checkURL(String url) {
        assertThat(this.bootConfig.getUrl(), equalTo(url));
    }
    
    @Then("自動更新が $auto であること")
    public void checkAutoUpdate(String auto) {
        assertThat(this.bootConfig.isAutoUpdate(), equalTo(auto.equals("1")));
    }
    
    @Then("アプリケーションのバージョンが $version であること")
    public void checkAppVersion(String version) throws Exception {
        JaloRunner jalo = new JaloRunner(this.appConfig);
        URLClassLoader loader = (URLClassLoader)jalo.createClassLoader();
        Class<?> cls = Class.forName(this.appConfig.getMainClass(), true, loader);
        Field f = cls.getField("version");
        String value = (String)f.get(null);

        assertThat(value, equalTo(version));
        
        loader.close();
    }
    
    @Then("tmpディレクトリのapp.xmlのバージョンが $version であること")
    public void checkTmpAppVersion(String version) throws Exception {
        Path tmpAppFile = this.tempPath.resolve(this.bootConfig.getTmpDir()).resolve(AppConfigBean.DEFAULT_FILENAME);
        
        AppConfigBean tmpAppConfig = new AppConfigBean();
        tmpAppConfig.read(tmpAppFile.toString());
        
        assertThat(tmpAppConfig.getVersion(), equalTo(version));
    }
    
    
    
    @AfterStories
    public void after() throws Exception {
        if (this.tempPath != null) {
            // ディレクトリの削除
            deletePath(this.tempPath);
        }
    }
    
    
    class DeleteVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
            Files.delete(path);
            return FileVisitResult.CONTINUE;
        }
        @Override
        public FileVisitResult postVisitDirectory(Path path, IOException exception) throws IOException {
            if (exception == null) {
                Files.delete(path);
                return FileVisitResult.CONTINUE;
            }
            else {
                throw exception;
            }
        }
    }
    
    public void deletePath(Path path) throws Exception {
        if (Files.exists(path)) {
            Files.walkFileTree(path, new DeleteVisitor());
        }
    }

}
