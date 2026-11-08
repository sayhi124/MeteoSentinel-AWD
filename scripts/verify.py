import json,urllib.request,urllib.error,time
from pathlib import Path
root=Path(__file__).resolve().parents[1]
cfg=json.loads((root/'Documentation/services.json').read_text(encoding='utf-8'))
def get(url):
 with urllib.request.urlopen(url,timeout=8) as r:return json.load(r)
for p in [8889,8762,8180]+list(range(8211,8218)):
 assert get(f'http://127.0.0.1:{p}/actuator/health')['status']=='UP',p
print('10 processus Spring Boot : UP')
c=get('http://127.0.0.1:8889/weather-service/default')
assert any(x['source'].get('design.stage')=='VALIDATION_1' for x in c['propertySources'])
print('Config Server fournit la configuration centrale')
for s in cfg:
 url='http://127.0.0.1:8180/api/'+s['path']
 d=get(url+'/design');assert d['service']==s['id'] and d['businessImplemented'] is False
 try:get(url+'/planned')
 except urllib.error.HTTPError as e:assert e.code==501
 else:raise AssertionError('Un endpoint métier doit déclarer 501')
 print(s['id']+' : routage Gateway / Eureka, état de conception, 501 vérifiés')
req=urllib.request.Request('http://127.0.0.1:8762/eureka/apps',headers={'Accept':'application/json'})
with urllib.request.urlopen(req,timeout=8) as r:apps=json.load(r)['applications']['application']
names={a['name'].lower() for a in apps}
assert all(s['id'] in names for s in cfg)
assert 'api-gateway' in names
print('8 clients enregistrés dans Eureka ; bases externes non connectées en mode conception')
print('Aucun endpoint métier simulé en succès. Les bases hétérogènes sont prévues pour après validation.')
