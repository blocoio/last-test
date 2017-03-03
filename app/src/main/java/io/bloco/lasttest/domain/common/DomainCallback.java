package io.bloco.lasttest.domain.common;

public interface DomainCallback<T> {

  void onSuccess(T t);

  void onError(Throwable throwable);
}
