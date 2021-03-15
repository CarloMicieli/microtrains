/*
   Copyright 2021 (C) Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.carlomicieli.web.usecases;

import io.github.carlomicieli.usecases.UseCase;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInput;
import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.web.viewmodels.DefaultHttpResultPresenter;
import io.micronaut.http.HttpResponse;
import io.micronaut.scheduling.TaskExecutors;
import io.netty.channel.EventLoopGroup;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class UseCaseController {

    private final EventLoopGroup eventLoopGroup;
    private final ExecutorService ioExecutor;

    public UseCaseController(
            EventLoopGroup eventLoopGroup, @Named(TaskExecutors.IO) ExecutorService ioExecutor) {
        this.eventLoopGroup = Objects.requireNonNull(eventLoopGroup);
        this.ioExecutor = Objects.requireNonNull(ioExecutor);
    }

    public <TInput extends UseCaseInput, TOutput extends UseCaseOutput>
            UseCaseExecution<TInput, TOutput> handle() {
        return new UseCaseExecution<>(eventLoopGroup, ioExecutor);
    }

    public static class UseCaseExecution<
            TInput extends UseCaseInput, TOutput extends UseCaseOutput> {
        private final EventLoopGroup eventLoopGroup;
        private final ExecutorService ioExecutor;
        private UseCase<TInput> useCase;
        private TInput input;
        private DefaultHttpResultPresenter<TOutput> presenter;

        public UseCaseExecution(EventLoopGroup eventLoopGroup, ExecutorService ioExecutor) {
            this.eventLoopGroup = eventLoopGroup;
            this.ioExecutor = ioExecutor;
        }

        public UseCaseExecution<TInput, TOutput> useCase(UseCase<TInput> useCase) {
            this.useCase = Objects.requireNonNull(useCase);
            return this;
        }

        public UseCaseExecution<TInput, TOutput> withInput(TInput input) {
            this.input = Objects.requireNonNull(input);
            return this;
        }

        public UseCaseExecution<TInput, TOutput> andPresenter(
                DefaultHttpResultPresenter<TOutput> presenter) {
            this.presenter = Objects.requireNonNull(presenter);
            return this;
        }

        public Single<HttpResponse<?>> run() {
            return Single.fromCallable(
                            () -> {
                                useCase.execute(input);
                                return true;
                            })
                    .observeOn(Schedulers.from(eventLoopGroup))
                    .<io.micronaut.http.HttpResponse<?>>map(vv -> presenter.viewModel())
                    .subscribeOn(Schedulers.from(ioExecutor));
        }
    }
}
