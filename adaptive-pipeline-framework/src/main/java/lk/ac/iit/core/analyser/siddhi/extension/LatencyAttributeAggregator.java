/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */


package lk.ac.iit.core.analyser.siddhi.extension;


import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.OperationNotSupportedException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.query.selector.attribute.aggregator.AttributeAggregator;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.util.HashMap;
import java.util.Map;

@Extension(
        name = "latency",
        namespace = "learner",
        description = "This extension returns the latency of aggregated events.",
        parameters = {
                @Parameter(name = "data",
                        description = "The value that needs to be aggregated for the latency.",
                        type = {DataType.LONG})

        },
        returnAttributes = @ReturnAttribute(
                description = "Returns double for long data type",
                type = {DataType.DOUBLE}),
        examples = @Example(
                syntax = "from inputStream#window.length(5)" +
                        "\nselect learner:latency(value) as latencyOfValues" +
                        "\ninsert into outputStream;",
                description = "This will returns the latency of aggregated values as a double " +
                        "value for each event arrival and expiry of fixed window length 5."
        )
)

public class LatencyAttributeAggregator extends AttributeAggregator {

    private int count = 0;
    private double totalLatency = 0;

    protected void init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (expressionExecutors.length != 1) {
            throw new OperationNotSupportedException("Latency aggregator has to have exactly 1 " +
                    "parameter, currently " + expressionExecutors.length + " parameters are " +
                    "provided");
        }

        Attribute.Type type = expressionExecutors[0].getReturnType();


    }

    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

    public Object processAdd(Object data) {
        this.count++;

        this.totalLatency += (long) data;
        return (this.totalLatency / this.count);
    }


    public Object processAdd(Object[] data) {

        return new IllegalStateException("Latency cannot process data array, but found " + data);

    }


    public Object processRemove(Object data) {
        this.count--;
        this.totalLatency -= (double) data;
        return (this.totalLatency / this.count);


    }

    public Object processRemove(Object[] data) {

        return new IllegalStateException("Latency cannot process data array, but found " +
                data);
    }

    @Override
    public boolean canDestroy() {

        return this.count == 0 && this.totalLatency == 0.0;
    }

    public Object reset() {
        this.count = 0;
        this.totalLatency = 0.0;
        return this.totalLatency;
    }

    public void start() {
    }

    public void stop() {
    }

    public Map<String, Object> currentState() {
        Map<String, Object> state = new HashMap<>();
        state.put("Latency", this.totalLatency);
        state.put("Count", this.count);
        return state;
    }

    public void restoreState(Map<String, Object> state) {
        this.totalLatency = (Double) state.get("Median");
        this.count = (Integer) state.get("Count");

    }


}

