# SimplePDFViewer [![Android](https://img.shields.io/badge/inactive-SimplePDFViewer-green.svg?style=true)](https://github.com/jesro/MobileApp)
Efficient PDF viewer supporting URLs, local storage, seamless zoom, and modular scalability with robust design patterns.

<img src="https://readme-typing-svg.demolab.com/?lines=Improved%20Version%20In%20Progress%20(Branch%20%60feature/kotlin-refactor-pdf-viewer-clean-arch-solid%60)&font=Fira%20Code&width=880&height=50&duration=3000&pause=1000%22%20alt=Improved%20Version">

> **Note:** The current repository contains the original Java implementation of the library. An improved version, featuring updates in Kotlin and additional functionality, has been developed in the branch `feature/kotlin-refactor-pdf-viewer-clean-arch-solid`. While the work is complete, the updates are being committed incrementally in parts over the coming days. The full sets of changes will be finalized and fully committed once all parts are integrated.

###### 
![Screenshot_20201004-093005](https://user-images.githubusercontent.com/10104522/95653681-096a1580-0b18-11eb-81ca-db33ce7fd01e.gif)

## Usage
###### Setup
This library is available on Maven.
```
implementation 'com.jespeakywikis.pdfviewer:pdfviewer:1.0.2'
```
###### Add this in your Manifest.xml
```
<uses-permission android:name="android.permission.INTERNET" />
```
###### Add this code to activate pdf viewer
```
new PDFView(your-context,pdf-url-goes-here);
```
###### Example
```
new PDFView(this,"http://www.pdf995.com/samples/pdf.pdf");
```
###### License
```
Copyright (c) 2020 Jes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
