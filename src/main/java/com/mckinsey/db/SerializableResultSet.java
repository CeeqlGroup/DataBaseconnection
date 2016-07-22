package com.mckinsey.db;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.GregorianCalendar;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerializableResultSet extends JsonSerializer<ResultSet> {

    private static final Logger logger = LogManager.getLogger(SerializableResultSet.class);

    @Override
    public Class<ResultSet> handledType() {
        return ResultSet.class;
    }

    @Override
    public void serialize(ResultSet rs, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            String[] columnNames = new String[numColumns];
            int[] columnTypes = new int[numColumns];

            for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = rsmd.getColumnLabel(i + 1);
                columnTypes[i] = rsmd.getColumnType(i + 1);
            }

            long start = System.nanoTime();

            gen.writeStartArray();

            while (rs.next()) {

                boolean b;
                long l;
                double d;
                byte[] bytes;

                gen.writeStartObject();

                for (int i = 0; i < columnNames.length; i++) {

                    gen.writeFieldName(columnNames[i].toLowerCase());

                    switch (columnTypes[i]) {

                        case Types.INTEGER:
                            l = rs.getInt(i + 1);
                            if (rs.wasNull()) {
                                gen.writeNull();
                            } else {
                                gen.writeNumber(l);
                            }
                            break;

                        case Types.BIGINT:
                            l = rs.getLong(i + 1);
                            if (rs.wasNull()) {
                                gen.writeNull();
                            } else {
                                gen.writeNumber(l);
                            }
                            break;

                        case Types.DECIMAL:
                        case Types.NUMERIC:
                            gen.writeNumber(rs.getBigDecimal(i + 1));
                            break;

                        case Types.FLOAT:
                        case Types.REAL:
                        case Types.DOUBLE:
                            d = rs.getDouble(i + 1);
                            if (rs.wasNull()) {
                                gen.writeNull();
                            } else {
                                gen.writeNumber(d);
                            }
                            break;

                        case Types.NVARCHAR:
                        case Types.VARCHAR:
                        case Types.LONGNVARCHAR:
                        case Types.LONGVARCHAR:
                            gen.writeString(rs.getString(i + 1));
                            break;

                        case Types.BOOLEAN:
                        case Types.BIT:
                            b = rs.getBoolean(i + 1);
                            if (rs.wasNull()) {
                                gen.writeNull();
                            } else {
                                gen.writeBoolean(b);
                            }
                            break;

                        case Types.BINARY:
                        case Types.VARBINARY:
                        case Types.LONGVARBINARY:
                            bytes = rs.getBytes(i + 1);
                            if (rs.wasNull())
                                gen.writeNull();
                            else
                                gen.writeBinary(bytes);
                            break;

                        case Types.TINYINT:
                        case Types.SMALLINT:
                            l = rs.getShort(i + 1);
                            if (rs.wasNull()) {
                                gen.writeNull();
                            } else {
                                gen.writeNumber(l);
                            }
                            break;

                        case Types.DATE:
                            provider.defaultSerializeDateValue(rs.getDate(i + 1), gen);
                            break;

                        case Types.TIMESTAMP:
                            provider.defaultSerializeDateValue(rs.getTimestamp(i + 1, GregorianCalendar.getInstance()), gen);
                            break;

                        case Types.BLOB:
                            final Blob blob = rs.getBlob(i + 1);
                            provider.defaultSerializeValue(blob.getBinaryStream(), gen);
                            blob.free();
                            break;

                        case Types.CLOB:
                            final Clob clob = rs.getClob(i + 1);
                            provider.defaultSerializeValue(clob.getCharacterStream(), gen);
                            clob.free();
                            break;

                        case Types.ARRAY:
                            throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type ARRAY");

                        case Types.STRUCT:
                            throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type STRUCT");

                        case Types.DISTINCT:
                            throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type DISTINCT");

                        case Types.REF:
                            throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type REF");

                        case Types.JAVA_OBJECT:
                        default:
                            provider.defaultSerializeValue(rs.getObject(i + 1), gen);
                            break;
                    }
                }
                gen.writeEndObject();
            }
            gen.writeEndArray();

            gen.getPrettyPrinter();

            logger.debug(gen.getPrettyPrinter());


            rs.close();

            long end = System.nanoTime();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

}