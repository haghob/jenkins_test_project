# app.py
from flask import Flask, jsonify
import requests

app = Flask(__name__)

def check_jenkins():
    try:
        response = requests.get('http://localhost:8081/api/json')
        if response.status_code == 200:
            return True
        else:
            return False
    except requests.exceptions.RequestException:
        return False

@app.route('/')
def home():
    if check_jenkins():
        return 'Jenkins est en ligne !'
    else:
        return 'Jenkins est hors ligne.'

if __name__ == '__main__':
    app.run(debug=True)
