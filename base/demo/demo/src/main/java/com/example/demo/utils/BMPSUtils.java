package com.example.demo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

@Slf4j
public class BMPSUtils {

	/**
	 * 解析sampleSheet文档，获取sampleName
	 *
	 * @param sampleSheetFile
	 * @param header
	 * @return
	 */
	public static List <String> parseSampleSheetForList(File sampleSheetFile, String header) {
		Assert.notNull(sampleSheetFile);
		Assert.isTrue(sampleSheetFile.exists());
		try {
			CsvReader reader = new CsvReader(sampleSheetFile.getAbsolutePath());
			boolean start = false;
			List <String> sampleInfoList = new ArrayList <>();
			List<String> filedList = new ArrayList<>();
			while (reader.readRecord()) {
				if ("[Data]".equals(reader.get(0))) {
					start = true;
					reader.readRecord();
					String rawRecord = reader.getRawRecord();
					filedList.addAll(Arrays.asList(rawRecord.split(",")));
					continue;
				}
				if (!start) {
					continue;
				}

				// AE打头的样本编号例如： AE1700555FFP。
				String headerValue = null;
				for (int i = 0; i < filedList.size(); i++) {
					if (StringUtils.containsIgnoreCase(filedList.get(i), header)) {
						headerValue = reader.get(i);
						break;
					}
				}
				//if (StringUtils.startsWith(headerValue, "AE")) {
				//	sampleInfoList.add(headerValue);
				//}
				sampleInfoList.add(headerValue);
			}
			reader.close();
			return sampleInfoList;
		} catch (FileNotFoundException fnfe) {
			// ignore
		} catch (IOException ioe) {
			log.debug("Parse sampleSheet file error!", ioe);
		}
		return null;
	}

	
	/**
	 * 解析sampleSheet文档
	 * 
	 * @param sampleSheetFile
	 * @return
	 */
	public static Map<String, String> parseSampleSheet(File sampleSheetFile) {
		Assert.notNull(sampleSheetFile);
		Assert.isTrue(sampleSheetFile.exists());
		try {
			CsvReader reader = new CsvReader(sampleSheetFile.getAbsolutePath());
			boolean start = false;
			Map<String, String> sampleInfoMap = new HashMap<>();
			while (reader.readRecord()) {
				if ("[Data]".equals(reader.get(0))) {
					start = true;
					reader.readRecord();
					continue;
				}
				if (!start) {
					continue;
				}
				String sampleId = reader.get(0);

				// AE打头的样本编号例如： AE1700555FFP。
				String sampleName = reader.get(1);
				if (StringUtils.isEmpty(sampleId)) {
					break;
				}
				// 以MP或者mp开头的sampleId为分子病理号
//				if (sampleId.startsWith("MP") || sampleId.startsWith("mp")) {
//					sampleInfoMap.put(sampleId, sampleName);
//				}
				sampleInfoMap.put(sampleId, sampleName);
			}
			reader.close();
			return sampleInfoMap;
		} catch (FileNotFoundException fnfe) {
			// ignore
		} catch (IOException ioe) {
			log.debug("Parse sampleSheet file error!", ioe);
		}
		return null;
	}

	/**
	 * 解析sampleSheet文档，指定表头字段拿到对应分子病理号的数据
	 *
	 * @param sampleSheetFile
	 * @param header
	 * @return
	 */
	public static Map<String, String> parseSampleSheet(File sampleSheetFile, String header) {
		Assert.notNull(sampleSheetFile);
		Assert.isTrue(sampleSheetFile.exists());
		try {
			CsvReader reader = new CsvReader(sampleSheetFile.getAbsolutePath());
			boolean start = false;
			Map<String, String> sampleInfoMap = new HashMap<>();
			List<String> filedList = new ArrayList<>();
			while (reader.readRecord()) {
				if ("[Data]".equals(reader.get(0))) {
					start = true;
					reader.readRecord();
					String rawRecord = reader.getRawRecord();
					filedList.addAll(Arrays.asList(rawRecord.split(",")));
					continue;
				}
				if (!start) {
					continue;
				}
				String sampleId = reader.get(0);

				// AE打头的样本编号例如： AE1700555FFP。
				String headerValue = null;
				for (int i = 0; i < filedList.size(); i++) {
					if (StringUtils.containsIgnoreCase(filedList.get(i), header)) {
						headerValue = reader.get(i);
						break;
					}
				}
				if (StringUtils.isEmpty(sampleId)) {
					break;
				}
				// 以MP或者mp开头的sampleId为分子病理号
//				if (sampleId.startsWith("MP") || sampleId.startsWith("mp")) {
//					sampleInfoMap.put(sampleId, sampleName);
//				}
				if (StringUtils.isEmpty(headerValue)) {
					sampleInfoMap.put(sampleId, "");
				} else {
					sampleInfoMap.put(sampleId, headerValue);
				}
			}

			reader.close();
			return sampleInfoMap;
		} catch (FileNotFoundException fnfe) {
			// ignore
		} catch (IOException ioe) {
			log.debug("Parse sampleSheet file error!", ioe);
		}
		return null;
	}
}
