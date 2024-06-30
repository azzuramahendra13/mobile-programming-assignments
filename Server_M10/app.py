from flask import Flask, request, url_for
from werkzeug.utils import secure_filename


app = Flask(__name__)

@app.route("/upload", methods=['POST'])
def upload():
    file = request.files['image']
    sender = request.form['sender']

    file.save(f'static/img/{sender}_{secure_filename(file.filename)}')
    return {
        "status": "OK"
    }

if __name__ == '__main__':
    app.run(debug=True, host="192.168.1.102", port=5000)