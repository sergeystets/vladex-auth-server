<template>
  <v-container>
    <v-layout row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-text>
            <h1 class="mb-2 di 1 text-left">{{ $t('label.sign-in') }}</h1>
            <v-container>
              <form @submit.prevent="onPhoneEntered">
                <v-layout row>
                  <v-flex xs12>
                    <v-text-field
                        name="phone"
                        @keydown="errors = []"
                        :label="$t('input.hint.phoneNumber')"
                        id="phone"
                        v-model="phone"
                        type="text"
                        :error-messages="errors"
                    ></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout>
                  <v-flex xs12>
                    <v-btn type="submit" :loading="verifyPhoneLoading">{{ $t('button.next') }}</v-btn>
                  </v-flex>
                </v-layout>
              </form>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import api from "@/server-api";

export default {
  data() {
    return {
      phone: '',
      verifyPhoneLoading: false,
      errors: []
    }
  },
  mounted() {
    this.phone = this.$store.getters.phone;
  },
  methods: {
    onPhoneEntered() {
      if (this.phone === undefined || this.phone === null || this.phone.trim() === "") {
        this.errors.push(this.$t('validation.phone.number.is.required'));
        return;
      }
      this.verifyPhoneLoading = true;
      api.signIn(this.phone).then(res => {
        this.verifyPhoneLoading = false;
        this.$store.dispatch("phoneVerified", {phone: this.phone, otpExpirationSeconds: res.data.otpExpirationSeconds});
        this.$router.push('/sign-in/otp');
      }).catch(err => {
        this.verifyPhoneLoading = false;
        if (err && err.response && err.response.data && err.response.data.errorCode === "PHONE_NUMBER_IS_NOT_REGISTERED") {
          this.errors.push(this.$t('validation.phone.number.is.not.registered'));
        }
      });
    }
  }
}
</script>
