package io.github.lumue.getdown.core.download.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lumue.getdown.core.common.persistence.ObjectBuilder;
import io.github.lumue.getdown.core.common.util.ObservableTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by lm on 13.04.16.
 */
public class Download extends ObservableTemplate implements  java.io.Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Download.class);


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((handle == null) ? 0 : handle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Download other = (Download) obj;
		if (handle == null) {
			if (other.handle != null) {
				return false;
			}
		} else if (!handle.equals(other.handle)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -1836164199304618485L;

	public static enum DownloadJobState {
		WAITING, RUNNING, ERROR, FINISHED, CANCELLED;
	}

	@JsonProperty("state")
	protected DownloadJobState downloadJobState = DownloadJobState.WAITING;

	@JsonProperty("downloadProgress")
	private DownloadProgress downloadProgress = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("error")
	private Throwable error = null;

	@JsonProperty("contentLocation")
	private ContentLocation contentLocation = null;

	@JsonProperty("handle")
	private final DownloadJobHandle handle;
	@JsonProperty("name")
	private String name;
	@JsonProperty("outputFilename")
	private final String outputFilename;
	@JsonProperty("url")
	private final String url;
	@JsonProperty("host")
	private final String host;
	@JsonProperty("downloadPath")
	private String downloadPath;

	private ContentLocationResolverRegistry contentLocationResolverRegistry;


	public DownloadJobState getState() {
		return downloadJobState;
	}


	public Optional<DownloadProgress> getDownloadProgress() {
		return Optional.ofNullable(downloadProgress);
	}

	public Optional<String> getMessage() {
		return Optional.ofNullable(message);
	}

	public Optional<Throwable> getError() {
		return Optional.ofNullable(error);
	}


	public DownloadJobHandle getHandle() {
		return handle;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public Optional<ContentLocation> getContentLocation() {
		return Optional.ofNullable(contentLocation);
	}

	@JsonCreator
	private Download(
			@JsonProperty("url") String url,
			@JsonProperty("outputFilename") String outputFilename,
			@JsonProperty("handle") DownloadJobHandle handle,
			@JsonProperty("state") DownloadJobState downloadJobState,
			@JsonProperty("contentLocation") ContentLocation contentLocation,
			@JsonProperty("downloadProgress") DownloadProgress downloadProgress, String name, String host) {
		super();
		this.outputFilename = outputFilename;
		this.url = url;
		this.handle = handle;
		this.downloadJobState = downloadJobState;
		this.name = name;
		this.host = host;
		this.contentLocation = contentLocation;
		this.downloadProgress = downloadProgress;
	}

	public Download(
			String name,
			String url,
			String outputFilename,
			String host,
			DownloadJobHandle handle) {
		super();
		this.name = name;
		this.host = host;
		this.outputFilename = outputFilename;
		this.url = url;
		this.handle = handle;
	}

	public Download(
			String name,
			String url,
			String outputFilename,
			String host) {
		this(name, url, outputFilename, host, new DownloadJobHandle());
	}


	protected void progress(DownloadProgress downloadProgress) {
		doObserved(() -> {
			this.downloadProgress = downloadProgress;
		});
	}


	protected void start() {
		doObserved(() -> {
			downloadJobState = DownloadJobState.RUNNING;
			message = "initializing...";
		});
	}

	protected void message(String message) {
		doObserved(() -> {
			downloadJobState = DownloadJobState.RUNNING;
			Download.this.message = message;
		});
	}

	protected void updateName(String name) {
		doObserved(() -> {
			this.name = name;
		});
	}



	protected void download() {
		doObserved(() -> {
			ContentLocation contentLocation = this.getContentLocation().get();
			message = "downloading from " + contentLocation.getUrl() + " to " + contentLocation.getFilename();
		});
	}

	protected void finish() {
		doObserved(() -> {
			downloadJobState = DownloadJobState.FINISHED;
		});
	}

	protected void error(Throwable e) {
		doObserved(() -> {
			downloadJobState = DownloadJobState.ERROR;
			message = "error: " + e.getLocalizedMessage();
		});
	}

	protected void setContentLocation(ContentLocation contentLocation) {
		this.contentLocation = contentLocation;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public void setContentLocationResolverRegistry(
			ContentLocationResolverRegistry contentLocationResolverRegistry) {
		this.contentLocationResolverRegistry = contentLocationResolverRegistry;
	}

	public ContentLocationResolverRegistry getContentLocationResolverRegistry() {
		return contentLocationResolverRegistry;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public String getHost() {
		return this.host;
	}

	public static class DownloadJobHandle implements Serializable {

		private static final long serialVersionUID = -7582907691952041979L;

		@JsonProperty("key")
		private String key;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public DownloadJobHandle() {
			this(UUID.randomUUID().toString());
		}

		@JsonCreator
		public DownloadJobHandle(@JsonProperty("key") String key) {
			this.key = key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			DownloadJobHandle other = (DownloadJobHandle) obj;
			if (key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!key.equals(other.key)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return key;
		}

	}

	public static class DownloadBuilder implements ObjectBuilder<DownloadJob> {
		protected String outputFilename;
		protected String url;
		protected String host;
		protected String name;

		public DownloadBuilder withOutputFilename(String outputFilename) {
			this.outputFilename = outputFilename;
			return this;
		}

		public DownloadBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public DownloadBuilder withUrl(String url) {
			this.url = url;
			this.host=URI.create(url).getHost();
			return this;
		}

		@Override
		public DownloadJob build() {
			return null;
		}
	}
}