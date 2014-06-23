package uk.ac.shef.dcs.oak.jate.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import uk.ac.shef.dcs.oak.jate.hadoop.WholeFileInputFormat;

public class TestJustesonKatzJob extends Configured implements Tool {

	private static final Log log = LogFactory.getLog(TestJustesonKatzJob.class);

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "JateMRJob");
		job.setJarByClass(TestJustesonKatzJob.class);
		job.setMapperClass(TestJustesonKatz.class);
		job.setNumReduceTasks(0);
		job.setInputFormatClass(WholeFileInputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		log.info("Submitting the job to Mapper");
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public void runJustesonKatzJob(String inputPath, String outputPath)
			throws Exception {
		String jobArgs[] = new String[] { inputPath, outputPath };
		int exitCode;
		try {
			exitCode = ToolRunner.run(new TestJustesonKatzJob(), jobArgs);
		} catch (Exception e) {
			throw e;
		}
		log.info("\n Job Status:" + exitCode);
	}

}
