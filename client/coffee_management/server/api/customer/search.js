import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const body = await readBody(event);
    const res = await axios.get(
      `http://127.0.0.1:8082/api/v1/customer/search?keyword=${body.query}`
    );
    return res.data;
  } catch (error) {
    console.log(error);
  }
});
