package io.github.lumue.getdown.job;

import io.github.lumue.getdown.downloader.DownloadProgress;
import io.github.lumue.getdown.job.DownloadJob.DownloadJobHandle;
import io.github.lumue.getdown.persistence.HasIdentity;
import io.github.lumue.getdown.persistence.ObjectBuilder;
import io.github.lumue.getdown.resolver.ContentLocationResolverRegistry;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface DownloadJob extends HasIdentity<DownloadJobHandle> {


	static class DownloadJobHandle implements Serializable {

		private static final long serialVersionUID = -7582907691952041979L;

		@JsonProperty("key")
		private String key;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		DownloadJobHandle() {
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
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DownloadJobHandle other = (DownloadJobHandle) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key;
		}

	}



	@Override
	public DownloadJobHandle getHandle();

	public DownloadJobProgress getProgress();

	public String getOutputFilename();

	public String getUrl();

	public DownloadJobProgress run(String downloadPath, ContentLocationResolverRegistry contentLocationResolverRegistry);

	public static class DownloadJobProgress {

		public static enum DownloadJobState {
			WAITING, RUNNING, ERROR, FINISHED;
		}

		private DownloadJobState downloadJobState = DownloadJobState.WAITING;

		private Optional<DownloadProgress> downloadProgress = Optional.empty();

		private Optional<String> message = Optional.empty();

		private Optional<Throwable> error = Optional.empty();

		public DownloadJobState getState() {
			return downloadJobState;
		}

		public void error(Exception e) {
			downloadJobState = downloadJobState.ERROR;
			message = Optional.of(e.getLocalizedMessage());
		}
	}

	public abstract static class AbstractDownloadJobBuilder implements ObjectBuilder<DownloadJob> {
		DownloadJobProgress progressListener = new DownloadJobProgress();
		String outputFilename;
		String url;

		public AbstractDownloadJobBuilder withProgressListener(DownloadJobProgress progressListener) {
			this.progressListener = progressListener;
			return this;
		}

		public AbstractDownloadJobBuilder withOutputFilename(String outputFilename) {
			this.outputFilename = outputFilename;
			return this;
		}

		public AbstractDownloadJobBuilder withUrl(String url) {
			this.url = url;
			return this;
		}
	}

	public abstract class AbstractDownloadJob implements DownloadJob, Serializable {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((handle == null) ? 0 : handle.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AbstractDownloadJob other = (AbstractDownloadJob) obj;
			if (handle == null) {
				if (other.handle != null)
					return false;
			} else if (!handle.equals(other.handle))
				return false;
			return true;
		}

		@JsonProperty("handle")
		private final DownloadJobHandle handle;
		@JsonProperty("progressListener")
		private final DownloadJobProgress progressListener;
		@JsonProperty("outputFilename")
		private final String outputFilename;
		@JsonProperty("url")
		private final String url;

		@JsonCreator
		private AbstractDownloadJob(
				@JsonProperty("url") String url,
				@JsonProperty("outputFilename") String outputFilename,
				@JsonProperty("progressListener") DownloadJobProgress progressListener,
				@JsonProperty("handle") DownloadJobHandle handle) {
			super();
			this.progressListener = progressListener;
			this.outputFilename = outputFilename;
			this.url = url;
			this.handle = handle;
		}

		public AbstractDownloadJob(
				String url,
				String outputFilename,
				DownloadJobProgress progressListener) {
			super();
			this.progressListener = progressListener;
			this.outputFilename = outputFilename;
			this.url = url;
			this.handle = new DownloadJobHandle();
		}

		@Override
		public DownloadJobHandle getHandle() {
			return handle;
		}

		@Override
		public DownloadJobProgress getProgress() {
			return progressListener;
		}

		@Override
		public String getOutputFilename() {
			return outputFilename;
		}

		@Override
		public String getUrl() {
			return url;
		}

	}

}