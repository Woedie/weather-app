# Weather App
## Final Bachelor Project

### About

Welcome to the wiki about my final bachelor project. My name is Milan Lamote, I study at VIVES Oostende but i'm completing this project at the University of Valladolid Spain.
I'm making an android application for the weather. The main goal is to show the last 24h temperatures and pollen information. I can choose ways to present the data to the client but they must be easy to read and use.

### Tools

I'm mainly using Android Studio Project. An IDE which is optimized for making android applications. There are a lot of features to this IDE: autocomplete, an instant build/deploy system, etc. Even if you don't have any device with you or you want to test your application on other devices as your own, there is an integrated virtualization for every device.
The programming itself is in Java.

### What does it do?

1. Download data:
The data is collected from aemet.es, a spanish website. Here I can collect the information necessary as a csv file. I download this file and store it on the phone itself. The file contains information from the past 24h. This data is stored and I plan to make another csv file where I can store data from a longer period of time for example one week or even one month.

2. Process data:
The data from the csv is read and processed into different formats:
  * Graph
This shows a graph of the data elements.
  * List
Here I use the ListView element to create a list of the different data elements.

### Installation

For now I have not been able to publish the apk. Even if I download it directly from github it doesn't work as intended. I'm trying to solve this.

### Usage

When launched you'll be presented the main page where there is some current information about temperature, rainfall, etc. If you click one of these you can get a more detailed look at the data concerning that topic.

### Credits

Author: Milan Lamote
Mentors VIVES: Tom Cordemans, Luc Vanhee.
Mentors UVa: Quiliano Isaac Moro Sancho, Javier Bastida Ibáñez.