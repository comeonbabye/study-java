package com.design_pattern.observer.observer_push_model;

public interface Observer {

	/**
     * 更新接口
     * @param state    更新的状态
     */
    public void update(String state);
}
