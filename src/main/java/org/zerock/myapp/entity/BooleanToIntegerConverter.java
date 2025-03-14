package org.zerock.myapp.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

//If autoApply is set to true, it will be automatically applied to all Boolean fields.	(***)
@Converter(autoApply = true)
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {

	
	@Override
	public Integer convertToDatabaseColumn(Boolean attr) {		// Boolean Attribute  -> Integer Column
//		log.debug("convertToDatabaseColumn({}) invoked.", attr);
		return (attr != null && attr) ? 1 : 0;			// true -> 1, false -> 0
	}

	@Override
	public Boolean convertToEntityAttribute(Integer col) {			// Integer Column -> Boolean Attribute
//		log.debug("convertToEntityAttribute({}) invoked.", col);
		return ( col != null && col == 1 );	// 1 -> true, 0 -> false
	}

} // end class
