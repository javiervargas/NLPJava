# NLPJava
Análisis de sentimiento utilizando el paquete de aprendizaje profundo NLP de Stanford 

<code>
ubuntu@ip-172-31-36-62:/var/www/$ java SentimentAnalyzer "This the best thing"<br>
Adding annotator tokenize
Adding annotator ssplit
Adding annotator parse
Loading parser from serialized file edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz ... done [1.8 sec].
Adding annotator sentiment
[Positivo]
ubuntu@ip-172-31-36-62:/var/www/$ java SentimentAnalyzer "This is a bad thing"<br>
Adding annotator tokenize
Adding annotator ssplit
Adding annotator parse
Loading parser from serialized file edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz ... done [2.1 sec].
Adding annotator sentiment
[Negativo]
ubuntu@ip-172-31-36-62:/var/www/$
</code>
