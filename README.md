# Lapis JSF Exporter

## What is it?
The goal of this project is to produce a JSF component that can be used to easily export data from other JSF components (tables, lists, trees, etc.) into a variety of file formats.

This project was born out of frustration with the PrimeFaces dataExporter component. p:dataExporter is really handy... if you only want to export the most boring p:dataTable. And you don't care about internationalization. And you don't need any file formats besides CSV, PDF, XML, and XLS. And you don't care that the XML exporter produces invalid XML. And... well, I think you get the idea.

I finally got fed up with all the problems and limitations. I decided to roll my own, and make the result available to anyone else who's in the same boat. Feedback is welcome, as are pull requests.

## Current Features
- Supports exporting data from the following components:
  - h:panelGrid
  - h:dataTable
  - rich:dataTable
  - p:dataTable (including dynamic columns, lazy loading, and p:columnGroup)
  - p:treeTable
  - p:dataList
- Has special formatting support for the following components:
  - h/p:commandLink
  - h:outputFormat
  - h:panelGroup
  - p:cellEditor
  - and others
- Supports exporting data to the following file formats:
  - CSV
  - XML
  - PDF
  - XLS/XLSX

## How To Get It
The artifacts are all in Maven Central, so you just need to add them to your POM.

First, you'll need the exporter core:

```xml
<dependency>
  <groupId>com.lapis.jsfexporter</groupId>
	<artifactId>jsf-exporter-core</artifactId>
	<version>1.0.0.Final</version>
</dependency>
```

Next, you'll need at least one export type module:

```xml
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-type-csv</artifactId>
	<version>1.0.0.Final</version>
</dependency>
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-type-excel</artifactId>
	<version>1.0.0.Final</version>
</dependency>
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-type-pdf</artifactId>
	<version>1.0.0.Final</version>
</dependency>
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-type-xml</artifactId>
	<version>1.0.0.Final</version>
</dependency>
```

Finally, you may need an export source module, depending on which component library you're using:

```xml
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-source-primefaces</artifactId>
	<version>1.0.0.Final</version>
</dependency>
<dependency>
	<groupId>com.lapis.jsfexporter</groupId>
	<artifactId>export-source-richfaces</artifactId>
	<version>1.0.0.Final</version>
</dependency>
```

## How To Use It
Using the exporter isn't much different from using the original p:dataExporter.

First, add the appropriate namespace to your markup:

```xml
xmlns:l="http://www.lapis.com/jsf/exporter"
```

Next, attach l:dataExporter to a non-AJAX ActionSource, such as a commandButton or commandLink:

```xml
<p:commandButton value="Export XLSX" ajax="false">
	<l:dataExporter source="table" fileType="excel" fileName="myfile"/>
</p:commandButton>
```

For more in-depth, working code examples, take a look at the jsf-exporter-test project.

## Improvements vs. p:dataExporter
- Massive pluggability
  - Can (theoretically) support exporting **any** JSF component from **any** library, not just PrimeFaces.
  - Define your own export source implementations and have them automatically work for any export type.
  - Define your own export type implementations and have them automatically work for any export source.
  - Define your own value formatter implementations and have them automatically be used.
- Better internationalization
  - Filenames may contain **any** UTF-8 characters, instead of just ASCII.
  - CSV export type allows you to change the separator character and character encoding, among other things.
  - CSV export type provides a special "UTF-8-with-bom" encoding so you can produce a CSV with non-ASCII characters that will open properly in MS Excel.
  - PDF export type uses a font with more than just Latin characters so text in languages like Chinese isn't missing.
- Better consistency
  - Each export source and export type can define its own configuration options as a POJO. The dataExporter tag just has one attribute for the export source configuration and another for the export type configuration. No more wondering if exporter X uses or ignores tag attribute Y.
  - Pre/post-processors are always called, instead of maybe being called depending on the export type.
- Some other neat stuff
  - Each export type is a separate Maven artifact with properly-defined transitive dependencies. No more manually adding iText or Apache POI to your POM.
  - XML export type automatically sanitizes tag names, instead of producing invalid XML.
  - Excel export type can produce XLSX files in addition to the older XLS format.

## License
All code is licensed under Apache 2.0, so you can use this in commercial projects. You also aren't required to release any changes you make, but you are encouraged to do so.
