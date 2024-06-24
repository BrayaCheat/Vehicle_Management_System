import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const body = await readBody(event);
    const { id } = event.context.params;
    const customerID = Number(id);
    if (isNaN(customerID)) {
      return "Missing id!";
    }
    const { name, email, phone } = body;
    const fd = new FormData();
    fd.append("name", name);
    fd.append("email", email);
    fd.append("phone", phone);
    const res = await axios.put(
      `http://127.0.0.1:8082/api/v1/customer/${customerID}`,
      fd
    );
    return res.data;
  } catch (error) {
    console.log(error);
  }
});
