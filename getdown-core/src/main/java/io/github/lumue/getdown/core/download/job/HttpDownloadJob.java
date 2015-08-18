package io.github.lumue.getdown.core.download.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lumue.getdown.core.download.downloader.ContentDownloader;
import io.github.lumue.getdown.core.download.downloader.HttpContentDownloader;
import io.github.lumue.getdown.core.download.job.DownloadJob.AbstractDownloadJob;
import io.github.lumue.getdown.core.download.resolver.ContentLocation;

public class HttpDownloadJob extends AbstractDownloadJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225589581609982485L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(HttpDownloadJob.class);

	private final static ContentDownloader DOWNLOADER = new HttpContentDownloader();

	private HttpDownloadJob(String url, String outputFilename) {
		super(url, outputFilename);
	}

	@Override
	public void run() {
		OutputStream outStream = null;
		try {
			LOGGER.debug("start download for url " + getUrl());
			start();

			resolve();

			if(DownloadJobState.ERROR.equals(getState()))
				return;
			
			download();
			
			ContentLocation contentLocation=getContentLocation().get();
			
			outStream = new FileOutputStream(getDownloadPath()+ File.separator
					+ contentLocation.getFilename());
			DOWNLOADER.downloadContent(URI.create(contentLocation.getUrl()),
					outStream,

			downloadProgress -> {
				progress( downloadProgress);
			} );

			outStream.flush();

			finish();

			LOGGER.debug("finished download for url " + getUrl());

		} catch (Throwable e) {
			error( e);
			LOGGER.error("Error running Job " + this + " :", e);
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static class HttpDownloadJobBuilder
			extends AbstractDownloadJobBuilder {

		@Override
		public DownloadJob build() {
			return new HttpDownloadJob(this.url, this.outputFilename);
		}

	}


	@Override
	public void cancel() {
		doObserved(()->
		{
			getDownloadProgress().ifPresent(progress -> {progress.cancel();});
			downloadJobState = DownloadJobState.CANCELLED;
		}); 
	}

}
