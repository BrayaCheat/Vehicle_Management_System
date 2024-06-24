import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const body = await readBody(event);
    const { name, email, phone } = body;
    const fd = new FormData();
    fd.append("name", name);
    fd.append("email", email);
    fd.append("phone", phone);
    const res = await axios.post(`http://127.0.0.1:8082/api/v1/customer`, fd);
    return res.data;
  } catch (error) {
    console.log(error);
  }
});
