package com.drippz.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.drippz.annotations.Column;
import com.drippz.annotations.Entity;
import com.drippz.annotations.Id;
import com.drippz.annotations.JoinColumn;
import com.drippz.customexceptions.ClassHasNoNameException;

public class MetaModel<T> {

	private Class<?> clazz;

	private PrimaryKeyField primaryKeyField;

	private List<ColumnField> columnFields;
	private LinkedList<ForeignKeyField> foreignKeyFields;

	// check for @Entity annotation
	public static MetaModel<Class<?>> of(Class<?> clazz) {

		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel object! Provided class " + clazz.getName()
					+ "is not annotated with @Entity");
		}

		return new MetaModel<>(clazz);
	}

	public MetaModel(Class<?> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>();
		this.foreignKeyFields = new LinkedList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				primaryKeyField = new PrimaryKeyField(field);
				continue;
			}
			if (field.isAnnotationPresent(Column.class)) {
				this.columnFields.add(new ColumnField(field));
				continue;
			}
			if (field.isAnnotationPresent(JoinColumn.class)) {
				this.foreignKeyFields.add(new ForeignKeyField(field));
			}
		}
		
	}
	
	public String getEntityName() {
		String entityName = clazz.getAnnotation(Entity.class).entityName();
		
		if ((entityName != "")) {
			return entityName;
		}
		else {
			entityName = clazz.getSimpleName();
			if ((entityName != ""))
			return entityName;
		}
		throw new ClassHasNoNameException("Class Name not assigned, and Class is Annonymous or not present in source");
	}
	
	// return all column fields of a metamodeled class
	public List<ColumnField> getColumns() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			// column ref variable will not be null if field has annotation
			Column column = field.getAnnotation(Column.class);

			if (column != null) {

				columnFields.add(new ColumnField(field));
			}
		}

		// add some extra logic in case class doesn't have any column fields

		if (columnFields.isEmpty()) {
			throw new RuntimeException("No columns found in: " + clazz.getName());
		}

		return columnFields;
	}

	public PrimaryKeyField getPrimaryKey() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			Id primaryKey = field.getAnnotation(Id.class);

			if (primaryKey != null) {
				return new PrimaryKeyField(field);
			}
		}

		throw new RuntimeException("Did not find a field annotated with @Id in " + clazz.getName());

	}

	public LinkedList<ForeignKeyField> getForeignKeys() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			JoinColumn foreignKey = field.getAnnotation(JoinColumn.class);

			if (foreignKey != null) {

				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}

		return foreignKeyFields;
	}

	public String getClassName() {
		return clazz.getName();
	}

	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}







}