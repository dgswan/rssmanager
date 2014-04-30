package models;

import play.jobs.Every;
import play.jobs.Job;

import java.util.List;

@Every("1h")
public class Updater extends Job {
    @Override
    public void doJob() throws Exception {
        List<Channel> channels = Channel.findAll();
        for (Channel channel :channels) {
            channel.update();
        }

    }

}
