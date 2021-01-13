<template>
  <v-container>
    <v-layout v-show="showOtpVerificationForm" row>
      <v-flex xs12 sm6 offset-sm3>
        <v-flex xs12>
          <v-btn v-on:click="goBack">
            <v-icon dark left>arrow_back</v-icon>
            Back
          </v-btn>
        </v-flex>
        <v-card>
          <v-card-text>
            <p class="mb-2 di 1 text-left">We've sent an SMS with activation code to your phone
              {{ phone }}</p>
            <v-container>
              <form ref="authForm" method="post" action="http://localhost:8282/login">
                <v-text-field hidden name="username" value="+380674591293"></v-text-field>
                <v-text-field hidden name="password" value="123ABC78"></v-text-field>
                <v-layout row>
                  <v-flex xs12>
                    <v-text-field
                        name="otp"
                        label="Code"
                        :loading="loading"
                        id="otp"
                        v-model="otp"
                        type="text"
                        required></v-text-field>
                  </v-flex>
                </v-layout>
                <p class="mb-2 di 1 text-left">It will expire in:
                  {{ expirationCounter | formatCounter }}</p>
              </form>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>

    <v-container v-show="!showOtpVerificationForm" style="height: 400px;">
      <v-row
          class="fill-height"
          align-content="center"
          justify="center"
      >
        <v-col
            class="subtitle-1 text-center"
            cols="12"
        >
          Singing in...
        </v-col>
        <v-col cols="6">
          <v-progress-linear
              indeterminate
              rounded
              height="6"
          ></v-progress-linear>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>

export default {

  data() {
    return {
      otp: '',
      expirationCounter: null,
      showOtpVerificationForm: true
    }
  },

  mounted() {
    this.expirationCounter = this.otpExpirationSeconds;
  },

  computed: {
    phone() {
      return this.$store.getters.phone;
    },
    loading() {
      return this.$store.getters.loadingOtp
    },
    otpExpirationSeconds() {
      return this.$store.getters.otpExpirationSeconds
    }
  },
  watch: {
    otp(value) {
      if (value.length >= 8) {
        this.showOtpVerificationForm = false;
        this.onOtpEntered();
      }
    },
    expirationCounter: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.expirationCounter--;
          }, 1000);
        }
      },
    },

  },
  methods: {
    onOtpEntered() {
      this.$refs.authForm.submit();
    },

    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>
