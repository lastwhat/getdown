package io.github.lumue.getdown.core.download.job;

import java.io.Serializable;
import java.util.Optional;

import io.github.lumue.getdown.core.common.persistence.HasIdentity;
import io.github.lumue.getdown.core.common.util.Observable;
import io.github.lumue.getdown.core.download.job.Download.DownloadJobState;
import io.github.lumue.getdown.core.download.job.Download.DownloadJobHandle;

public interface DownloadJob extends HasIdentity<DownloadJobHandle>,Serializable,Observable,Runnable {

	@Override
	DownloadJobHandle getHandle();

	Optional<Throwable> getError();

	Optional<String> getMessage();

	Optional<DownloadProgress> getDownloadProgress();

	DownloadJobState getState();

	String getOutputFilename();

	String getUrl();

	String getHost();

	String getName();

	Optional<ContentLocation> getContentLocation();

	void setDownloadPath(String downloadPath);

	void setContentLocationResolverRegistry(ContentLocationResolverRegistry contentLocationResolverRegistry);

	void run();


	void cancel();

	
	

	

}
