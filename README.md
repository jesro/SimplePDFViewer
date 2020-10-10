# SimplePDFViewer [![Android](https://img.shields.io/badge/active-SimplePDFViewer-green.svg?style=true)](https://github.com/jesro/MobileApp)
Easily create your pdf viewer screen with url

Android SimplePDFViewer

SimplePDFViewer library allows you to view pdf from url with less effort

![Screenshot_20201004-093005](https://user-images.githubusercontent.com/10104522/95653681-096a1580-0b18-11eb-81ca-db33ce7fd01e.gif)

## Usage
###### Setup
This library is available on Jcenter.
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