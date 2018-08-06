package @packageName@;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppLauncher {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
        new SpringApplicationBuilder(AppLauncher.class).run(args);
        context.addApplicationListener(new ApplicationPidFileWriter());
        context.addApplicationListener(new EmbeddedServerPortFileWriter());
    }

}