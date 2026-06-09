# BUILD STAGE
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install
# Cria pastas vazias public e src temporariamente para o Docker build não quebrar antes de escrever o código
RUN mkdir public src && echo "<html><body>Hello</body></html>" > public/index.html && echo "console.log('hi');" > src/index.js
COPY . .
RUN npm run build

# RUNTIME STAGE
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80