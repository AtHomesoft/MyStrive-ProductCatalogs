package com.mystrive.products;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

public class ProductNumberProcessor extends CellProcessorAdaptor implements StringCellProcessor {
    public ProductNumberProcessor() {
    }

    public ProductNumberProcessor(CellProcessor next) {
        super(next);
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        String formattedNumber;
        validateInputNotNull(value, context);

        if (!(value instanceof String)) {
            String actualClassName = value.getClass().getName();
            String msg = String.format("the input value should be of type String but is of type %s", actualClassName);
            throw new SuperCsvCellProcessorException(msg, context, this);
        }

        try {
            Integer result = Integer.valueOf((String) value);
            formattedNumber = String.format("%06d", result);
        } catch (NumberFormatException var5) {
            formattedNumber = (String) value;
        }

        return this.next.execute(formattedNumber, context);
    }
}
