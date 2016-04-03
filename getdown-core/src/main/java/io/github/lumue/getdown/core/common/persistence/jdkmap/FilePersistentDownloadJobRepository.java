package io.github.lumue.getdown.core.common.persistence.jdkmap;

import io.github.lumue.getdown.core.common.persistence.ObjectBuilder;
import io.github.lumue.getdown.core.download.job.DownloadJob;
import io.github.lumue.getdown.core.download.job.DownloadJob.DownloadJobHandle;
import io.github.lumue.getdown.core.download.job.DownloadJobRepository;

import java.io.File;

/**
 * not thread safe, in memory repository
 * 
 * @author lm
 *
 */
public class FilePersistentDownloadJobRepository extends
		JdkSerializableRepository<ObjectBuilder<DownloadJob>, DownloadJobHandle, DownloadJob>
		implements
		DownloadJobRepository {

	public FilePersistentDownloadJobRepository(String filename) {
		super(filename + File.separator + "download-jobs.bin");
	}


}
