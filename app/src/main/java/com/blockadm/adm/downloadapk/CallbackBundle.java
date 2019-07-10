package com.blockadm.adm.downloadapk;

//在接口上定义泛型
public interface CallbackBundle<T> { 
	 
	abstract void callback(T t);// 定义抽象方法，抽象方法的形式参数就是泛型类型,主要是为了防止传入传出参数类型不同,导致崩溃.
}

