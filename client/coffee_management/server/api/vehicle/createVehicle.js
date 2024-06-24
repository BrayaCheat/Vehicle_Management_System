import { IncomingForm } from "formidable";
import axios from "axios";

export default defineEventHandler(async (event) => {
  return new Promise((resolve, reject) => {
    const form = new IncomingForm();

    form.parse(event.req, async (err, fields, files) => {
      if (err) {
        console.error("Error parsing the form:", err);
        reject({ error: "Error parsing the form data" });
        return;
      }

      const { model, year, color, carCondition, type, price, file } = fields;

      const fd = new FormData();
      fd.append("model", model);
      fd.append("year", year);
      fd.append("color", color);
      fd.append("carCondition", carCondition);
      fd.append("file", file); // Use filepath to get the file location
      fd.append("type", type);
      fd.append("price", price);

      try {
        const res = await axios.post("http://127.0.0.1:8082/api/v1/car", fd, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        resolve(res.data);
      } catch (error) {
        console.error("Error posting the data:", error);
        reject({ error: "Error posting the data" });
      }
    });
  });
});
