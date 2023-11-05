# JsonDataWebscrapper
Takes a URL of a charting website gets the html and looks for a javascript object that contains the input search name and takes out the object into a json file.

In the main java file, you can change the url of the website you want to scrape. you will have to look into the html of your website and verify if the chart data is being stored in a script tag. 
if it is, you can use the name found in the first layer of the object to extract that object. 

