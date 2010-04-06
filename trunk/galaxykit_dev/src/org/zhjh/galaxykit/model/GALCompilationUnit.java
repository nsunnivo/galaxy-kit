package org.zhjh.galaxykit.model;

import java.util.List;
import java.util.Map;

public class GALCompilationUnit {
	public String filePath;
	public List<String> includeList;
	public Map<String, GALFunction> functionMap;
	public Map<String, GALStruct> structMap;
}
