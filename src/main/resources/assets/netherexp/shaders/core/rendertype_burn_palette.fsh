#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D Sampler0;
uniform sampler2D SceneDepthBuffer;
uniform sampler2D PaletteSampler;

uniform float LumiTransparency;
uniform float DepthFade;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform mat4 InvProjMat;
uniform vec2 ScreenSize;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 viewSpacePos;

out vec4 fragColor;

void main() {
    vec4 texColor = texture(Sampler0, texCoord0);
    vec3 color = texColor.rgb;
    float alpha = texColor.a;

    float brightness = color.r;
    const float epsilon = 0.05;
    int colorIndex = -1;

    if (abs(brightness - 1.0) < epsilon) colorIndex = 0;
    else if (abs(brightness - 0.717647) < epsilon) colorIndex = 1;
    else if (abs(brightness - 0.529412) < epsilon) colorIndex = 2;
    else if (abs(brightness - 0.4) < epsilon) colorIndex = 3;
    else if (abs(brightness - 0.309804) < epsilon) colorIndex = 4;
    else if (abs(brightness - 0.239216) < epsilon) colorIndex = 5;

    if (colorIndex != -1) {
        ivec3 col = ivec3(round(vertexColor.rgb * 255.0));
        int paletteIndex = col.r | (col.g << 8) | (col.b << 16);
        ivec2 texSize = textureSize(PaletteSampler, 0);
        int palettesPerRow = texSize.x / 6;
        int pRow = paletteIndex / palettesPerRow;
        int pCol = paletteIndex % palettesPerRow;
        float u = (float(pCol * 6 + colorIndex) + 0.5) / float(texSize.x);
        float v = (float(pRow) + 0.5) / float(texSize.y);
        vec4 paletteColor = texture(PaletteSampler, vec2(u, v));
        color = paletteColor.rgb;
    }

    vec4 finalColor = vec4(color, alpha) * vertexColor.a * ColorModulator;
    vec4 fogged = applyFog(finalColor, FogStart, FogEnd, FogColor, vertexDistance);
    if (fogged.a == 0.0) discard;

    float sceneDepthClip = getDepth(SceneDepthBuffer, gl_FragCoord.xy / ScreenSize);
    vec3 sceneViewSpace = viewSpaceFromDepth(sceneDepthClip, gl_FragCoord.xy / ScreenSize, InvProjMat);
    float depthFade = applyDepthFade(sceneViewSpace.z, viewSpacePos.z, DepthFade);
    fogged.a *= depthFade;
    fragColor = fogged;
}