package tdd;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Range {
    long lowerBound;
    long upperBound;


    public boolean isBefore(Range otherRange) {
        return getUpperBound()< otherRange.getLowerBound();
    }

    public boolean isAfter(Range otherRange) {
        return getLowerBound()>otherRange.getUpperBound();
    }

    public boolean isConcurrent(Range otherRange){
        return getUpperBound()>otherRange.getLowerBound() || otherRange.getUpperBound()>lowerBound;
    }

    public boolean contains(long value){
        return lowerBound < value && value < upperBound;
    }

    public List<Long> asList(){
        List<Long> list = new ArrayList<>();
        for (long i = lowerBound; i <= upperBound ; i++) {
            list.add(i);
        }
        return list;
    }

    public Iterator<Long> asIterator(){
        return this.asList().iterator();
    }



}
