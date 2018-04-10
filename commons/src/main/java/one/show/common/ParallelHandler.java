package one.show.common;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ParallelHandler<T_INPUT, T_OUTPUT> {
	private static Logger log = LoggerFactory.getLogger(ParallelHandler.class);


	int poolSize;
	public ExecutorService executor;

	public ParallelHandler() {
		poolSize = 300;
		executor = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * 实际任务
	 *
	 * @return
	 * @throws Exception
	 */
	public abstract T_OUTPUT handle(T_INPUT item);


	public List<T_OUTPUT> execute(List<T_INPUT> list, int singleThreadthreshold) {
		List<Callable<T_OUTPUT>> callableList = (List<Callable<T_OUTPUT>>) CollectionUtils.collect(list, new Transformer<T_INPUT, Callable<T_OUTPUT>>() {
			@Override
			public Callable transform(final T_INPUT item) {
				return new Callable<T_OUTPUT>() {
					@Override
					public T_OUTPUT call() throws Exception {
						return handle(item);
					}
				};
			}
		});
		try {
			final long timewait = (list.size() + poolSize - 1) / poolSize * singleThreadthreshold;
			List<Future<T_OUTPUT>> futureList = executor.invokeAll(callableList, timewait, TimeUnit.MILLISECONDS);
			executor.shutdown();
			return (List<T_OUTPUT>) CollectionUtils.collect(futureList, new Transformer<Future, T_OUTPUT>() {
				@Override
				public T_OUTPUT transform(Future future) {
					try {
						T_OUTPUT videoStat = (T_OUTPUT) future.get();
						return videoStat;
					} catch (InterruptedException e) {
						log.error("interrupt", e);
					} catch (ExecutionException e) {
						log.error("ExecutionException", e);
					} catch (CancellationException e) {
						log.error("timeout["+timewait+"]", e);
					}
					return null;
				}
			});
		} catch (InterruptedException e) {
			log.error("interrupt", e);
		}
		return null;
	}

	public List<T_OUTPUT> execute(List<T_INPUT> list) {
		return execute(list, 2000);
	}
}