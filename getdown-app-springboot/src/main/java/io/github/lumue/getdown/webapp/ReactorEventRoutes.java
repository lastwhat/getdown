package io.github.lumue.getdown.webapp;

import io.github.lumue.getdown.webapp.websocket.DownloadWebsocketController;
import io.github.lumue.getdown.core.download.task.DownloadTaskRepository;
import io.github.lumue.getdown.core.download.job.ThrottlingDownloadJobEventTap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.bus.EventBus;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

/**
 * setup event routing
 *
 * Created by lm on 19.10.16.
 */
@Component
@Lazy(false)
public class ReactorEventRoutes {

	public static final String THROTTELED_DOWNLOADS = "throtteled-downloads";
	private final DownloadWebsocketController websocketController;

	private final DownloadTaskRepository downloadJobRepository;

	private final EventBus eventbus;

	private final ThrottlingDownloadJobEventTap throttlingDownloadJobEventTap;

	@Autowired
	public ReactorEventRoutes(DownloadWebsocketController websocketController,
	                          DownloadTaskRepository downloadJobRepository,
	                          EventBus eventbus) {
		this.websocketController = websocketController;
		this.downloadJobRepository = downloadJobRepository;
		this.eventbus = eventbus;
		this.throttlingDownloadJobEventTap = new ThrottlingDownloadJobEventTap(eventbus, THROTTELED_DOWNLOADS, 5000);
	}

	/**
	 * sets up subscriptions for reactor events
	 */
	@PostConstruct
	public void setup(){
		this.eventbus.on($("downloads"),throttlingDownloadJobEventTap);
		this.eventbus.on($(THROTTELED_DOWNLOADS),websocketController);
	}
}
