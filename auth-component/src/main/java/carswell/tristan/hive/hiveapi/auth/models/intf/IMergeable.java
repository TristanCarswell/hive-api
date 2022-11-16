package carswell.tristan.hive.hiveapi.auth.models.intf;

public interface IMergeable<T> {
    
    T merge(T merger);
    
}
