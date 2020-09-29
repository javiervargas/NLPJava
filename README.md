# NLPJava
Análisis de sentimiento utilizando el paquete de aprendizaje profundo NLP de Stanford 

```
ubuntu@ip-172-31-36-62:/var/www/$ java SentimentAnalyzer "This the best thing"
Adding annotator tokenize
Adding annotator ssplit
Adding annotator parse
Loading parser from serialized file edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz ... done [1.8 sec].
Adding annotator sentiment
[Positivo]
ubuntu@ip-172-31-36-62:/var/www/$ java SentimentAnalyzer "This is a bad thing"
Adding annotator tokenize
Adding annotator ssplit
Adding annotator parse
Loading parser from serialized file edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz ... done [2.1 sec].
Adding annotator sentiment
[Negativo]
```
