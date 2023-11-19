package edu.project3.LogStatisticsDisplay;

import edu.project3.LogStatistics;
import edu.project3.ResponseCode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.steppschuh.markdowngenerator.table.Table;

public class LogStatisticsMarkdown implements LogStatisticsDisplay {
    private static final int DEFAULT_TOP = 3;

    @Override
    public void print(LogStatistics logStatistics) {
        print(logStatistics, DEFAULT_TOP);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public String getStatisticsDisplay(LogStatistics logStatistics, int numberOfTop) {
        StringBuilder result = new StringBuilder();
        result.append("#### Общая информация\n\n");
        result.append(getGeneralInformation(logStatistics));
        result.append("\n");

        result.append("#### Запрашиваемые ресурсы\n\n");
        result.append(getRequestedResources(logStatistics, numberOfTop).toString());
        result.append("\n");

        result.append("#### Коды ответа\n\n");
        result.append(getResponseCodes(logStatistics).toString());
        result.append("\n");
        return result.toString();
    }

    private Table getGeneralInformation(LogStatistics logStatistics) {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
            .addRow("Метрика", "Значение");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String fromDate = logStatistics.from().isEmpty() ? "-" : logStatistics.from().get().format(dateTimeFormatter);
        String toDate = logStatistics.to().isEmpty() ? "-" : logStatistics.to().get().format(dateTimeFormatter);
        tableBuilder.addRow("Файл(ы)", logStatistics.files().toString());
        tableBuilder.addRow("Начальная дата", fromDate);
        tableBuilder.addRow("Конечная дата", toDate);
        tableBuilder.addRow("Количество запросов", String.valueOf(logStatistics.numberOfRequests()));
        tableBuilder.addRow("Средний размер ответа", String.valueOf(logStatistics.averageResponseSizeInBytes()));
        return tableBuilder.build();
    }

    @SuppressWarnings("MultipleStringLiterals")
    private Table getRequestedResources(LogStatistics logStatistics, int numberOfTop) {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
            .addRow("Ресурс", "Количество");

        List<Map.Entry<String, Long>> topResources =
            new ArrayList<>(logStatistics.numberOfCertainResources().entrySet());
        topResources.sort(Map.Entry.comparingByValue());
        int number = 0;
        for (int i = topResources.size() - 1; i >= 0; --i) {
            tableBuilder.addRow(topResources.get(i).getKey(), topResources.get(i).getValue());
            ++number;
            if (number == numberOfTop) {
                break;
            }
        }
        return tableBuilder.build();
    }

    private Table getRequestedResources(LogStatistics logStatistics) {
        return getRequestedResources(logStatistics, DEFAULT_TOP);
    }

    private Table getResponseCodes(LogStatistics logStatistics, int numberOfTop) {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_CENTER, Table.ALIGN_CENTER)
            .addRow("Код", "Имя", "Количество");

        List<Map.Entry<ResponseCode, Long>> topResponseCodes =
            new ArrayList<>(logStatistics.responseCodes().entrySet());
        topResponseCodes.sort(Map.Entry.comparingByValue());
        int number = 0;
        for (int i = topResponseCodes.size() - 1; i >= 0; --i) {
            tableBuilder.addRow(
                topResponseCodes.get(i).getKey().number(),
                topResponseCodes.get(i).getKey().description(),
                topResponseCodes.get(i).getValue()
            );
            ++number;
            if (number == numberOfTop) {
                break;
            }
        }

        return tableBuilder.build();
    }

    private Table getResponseCodes(LogStatistics logStatistics) {
        return getResponseCodes(logStatistics, DEFAULT_TOP);
    }
}
