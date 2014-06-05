package models;

import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;

@Every("1min")
public class Updater extends Job {
    @Override
    public void doJob() throws Exception {
        Logger.info("qq");
        List<Channel> channels = Channel.findAll();
        for (Channel channel :channels) {
            channel.update();
        }

    }

}
