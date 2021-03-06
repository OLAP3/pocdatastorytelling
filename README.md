**POC for data narrative conceptual model** 

This web application developed in Java using Spring, d3.js, JFreeChart and Apache PDFBox aims at helping authors to organize their work when  exploring data to generate a visual narrative. 

The interface consists essentially of text areas where the author can declare their goal, analytical questions, messages, characters, measures, episodes and acts. The author starts a new narrative with a goal, and then express analytical questions.
For each such question, the author can try the different collectors, and inspect their answers. If the findings brought by a collector are found worth adding to the narrative, they are turned into messages, for which the author must declare at least one character and one measure. Then an episode can be created, only if it can be attached to an act and a message, that must have been declared beforehand.

**Screenshot** 

Here is a screenshot of the current version.

![screenshot](/images/screenshot-A1E2.png)


**To do**

- Improve PDF and SQL notebook rendering (e.g., print nice SQL tables in the PDF)
- Improve handling of Describe results
- Improve SQL viz generation (check column names, types, number, etc.)
- Improve console messages (more messages, more meaningful, include advices, etc.)
- Improve insertion log (summarize long texts, etc.)
- Improve storage/loading of narrative to/from the DB (esp. enable browsing stored narratives)
- Enable schema change (connect to other DB, use csv files, etc.)
- Enable multi-user and story sharing
- Generate more stories
- Add guidelines for using the app
- Add collectors and dashboard/components
- Enable assistants (eg, story recommendation)
- Test, test, test
