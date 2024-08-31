FROM node:20

RUN npm install -g @angular/cli@17.3.5

WORKDIR /projects

COPY package.json package-lock.json ./
#RUN npm install --no-optional

COPY . .

CMD ["ng", "serve", "--host=0.0.0.0"]
