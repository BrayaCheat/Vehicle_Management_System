import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const { id } = event.context.params;
    const customerID = Number(id);

    if (isNaN(customerID)) {
      throw new Error("Invalid ID Parameter!");
    }
    const res = await axios.get(
      `http://127.0.0.1:8082/api/v1/customer/${customerID}`
    );
    return res.data;
  } catch (error) {
    throw error;
  }
});
