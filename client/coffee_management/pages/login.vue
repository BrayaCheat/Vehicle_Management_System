<template>
  <section id="auth-form">
    <ULandingCard data-aos="zoom-in">
      <form
        enctype="multipart/form-data"
        @submit.prevent="handleLogin"
        class="w-[400px] flex flex-col gap-6 p-3"
      >
        <div class="flex flex-col mb-6">
          <div class="flex items-center justify-between">
            <h1 class="font-bold text-lg">Vehicles Management System</h1>
          </div>
          <p class="text-sm">Signin as adminstrator</p>
        </div>

        <div class="w-full grid gap-3">
          <UFormGroup label="Email">
            <UInput
              type="email"
              placeholder="you@example.com"
              icon="i-heroicons-envelope"
              v-model="email"
              required
            />
          </UFormGroup>

          <UFormGroup label="Password">
            <UInput
              type="password"
              placeholder="**********"
              icon="i-heroicons-lock-closed"
              v-model="password"
              required
            />
          </UFormGroup>

          <div class="h-4">
            <span v-if="errorMsg" class="text-sm text-red-500">{{
              errorMsg
            }}</span>
          </div>
        </div>

        <UButton label="Continue" block type="submit" />

        <UDivider label="Or" />

        <UButton
          icon="i-simple-icons-github"
          label="Continue with Github"
          color="gray"
          block
          @click="signInWithOAuth('github')"
        />

        <UButton
          icon="i-logos:google-icon"
          label="Continue with Google"
          color="gray"
          block
          @click="signInWithOAuth('google')"
        />

        <!-- <UButton
        icon="i-logos:facebook"
        label="Sign in with Facebook"
        color="gray"
        block
        /> -->

        <span class="text-sm text-center"
          >By signing in, you agree to our
          <a href="" class="text-primary">Term of Service.</a></span
        >
      </form>
    </ULandingCard>
  </section>
</template>

<script setup>
definePageMeta({
  layout: "auth",
});

const supabase = useSupabaseClient();
const email = ref(null);
const password = ref(null);
const errorMsg = ref(false);
const user = useSupabaseUser();
const toast = useToast();

const handleLogin = async () => {
  try {
    const { error, data } = await supabase.auth.signInWithPassword({
      email: email.value,
      password: password.value,
    });
    if (error) {
      errorMsg.value = error.message;
    }
    return navigateTo("/");
  } catch (error) {
    console.log(error);
  }
};

const signInWithOAuth = async (prov) => {
  try {
    const { error } = await supabase.auth
      .signInWithOAuth({
        provider: prov,
        options: {
          redirectTo: "http://localhost:3000/callBack",
        },
      })
      .then((res) => console.log(res.data));
  } catch (error) {
    console.log(error);
  }
};
</script>
