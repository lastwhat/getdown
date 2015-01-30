package io.github.lumue.getdown.application;

import io.github.lumue.getdown.application.DownloadJob.DownloadJobBuilder;
import io.github.lumue.getdown.application.DownloadJob.DownloadJobHandle;
import io.github.lumue.getdown.downloader.ContentDownloader.DownloadState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

/**
 * not thread safe, in memory repository
 * 
 * @author lm
 *
 */
@Component
class VolatileDownloadJobRepository implements DownloadJobRepository {

	private final Map<DownloadJobHandle, DownloadJob> jobMap = new HashMap<DownloadJobHandle, DownloadJob>();

	@Override
	public DownloadJob create(DownloadJobBuilder downloadJobBuilder) {
		DownloadJob job = downloadJobBuilder.build();
		jobMap.put(job.getHandle(), job);
		return job;
	}

	@Override
	public Iterable<DownloadJob> list() {
		return java.util.Collections.unmodifiableCollection(jobMap.values());
	}

	@Override
	public Iterable<DownloadJob> findByDownloadState(DownloadState downloadState) {
		return new Iterable<DownloadJob>() {

			@Override
			public Iterator<DownloadJob> iterator() {
				return jobMap.values().stream().filter(new Predicate<DownloadJob>() {

					@Override
					public boolean test(DownloadJob t) {
						return downloadState.equals(t.getProgressListener().getState());
					}

				}).iterator();
			}
		};

	}

	@Override
	public void remove(DownloadJobHandle handle) {
		jobMap.remove(handle);
	}

	@Override
	public DownloadJob get(DownloadJobHandle handle) {
		return jobMap.get(handle);
	}



}